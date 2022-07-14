package io.jpress.web.admin;


import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Page;
import io.jboot.db.model.Columns;
import io.jboot.utils.CookieUtil;
import io.jboot.utils.RequestUtil;
import io.jboot.utils.StrUtil;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jpress.JPressConsts;
import io.jpress.core.menu.annotation.AdminMenu;
import io.jpress.model.Role;
import io.jpress.model.SiteInfo;
import io.jpress.service.RoleService;
import io.jpress.service.SiteInfoService;
import io.jpress.web.base.AdminControllerBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequestMapping(value = "/admin/site", viewPath = JPressConsts.DEFAULT_ADMIN_VIEW)
public class _SiteController extends AdminControllerBase {


    @Inject
    private SiteInfoService siteInfoService;

    @Inject
    private RoleService roleService;


    @AdminMenu(text = "站点", groupId = JPressConsts.SYSTEM_MENU_SYSTEM, order = 7)
    public void list() {

        Columns columns = new Columns();
        Page<SiteInfo> page = siteInfoService.paginateByColumns(getPagePara(), getPageSizePara(), columns, "created desc");
        setAttr("page", page);

        //获取site id
        Long siteId = getLong();

        //根据site id 查询 site信息
        SiteInfo siteInfo = siteInfoService.findById(siteId);
        if (siteInfo != null) {
            setAttr("siteInfo", siteInfo);
        }

        List<Role> roleList = new ArrayList<>();

        //查询site 对象的角色 信息
        if (siteId == null) {
            roleList = roleService.findAll();
        } else {
            roleList = roleService.findListBySiteId(siteId);
        }

        if (!roleList.isEmpty()) {
            setAttr("roleList", roleList);

        }


        render("site/site_list.html");
    }


    /**
     * 站点数据  保存到数据库
     */
    public void doSave() {

        SiteInfo siteInfo = getBean(SiteInfo.class, "siteInfo");

        if (siteInfo == null) {
            renderFailJson("保存失败");
            return;
        }

        //如果设置了为默认站点
//        if (siteInfo.getWithLangDefault()) {
//            //查询是否有默认站点
//            SiteInfo siteInfoByDefault = siteInfoService.findLangDefaultSite();
//
//            //如果siteInfoByDefault 不为 null 就是已经有啦more站点
//            // 如果是修改 那么修改的不是 默认站点的话 不行
//            // 如果是新建 那么在已经有默认站点的情况下  不行
//            if (siteInfoByDefault != null && (siteInfo.getId() == null || (siteInfo.getId() != null && !siteInfo.getId().equals(siteInfoByDefault.getId())))) {
//                renderFailJson("已经有默认站点,请重新设置");
//                return;
//            }
//        }

        siteInfo.saveOrUpdate();

        //获取所有id
        Long[] roleIds = getParaValuesToLong("roleId");

        //更新中间表
        siteInfoService.saveOrUpdateSiteRoleMapping(siteInfo.getId(), roleIds);

        renderOkJson();

    }


    public void delById() {
        render(siteInfoService.deleteById(getIdPara()) ? OK : FAIL);
    }


    public void delByIds() {

        Set<String> idsSet = getParaSet("ids");
        render(siteInfoService.batchDeleteByIds(idsSet.toArray()) ? OK : FAIL);

    }


    public void change() {
        SiteInfo siteInfo = siteInfoService.findById(getParaToLong("id"));
        if (siteInfo != null) {
            CookieUtil.put(this, JPressConsts.COOKIE_ADMIN_SITE_ID, siteInfo.getSiteId());
        } else {
            CookieUtil.remove(this, JPressConsts.COOKIE_ADMIN_SITE_ID);
        }


        String referer = RequestUtil.getReferer(getRequest());
        if (StrUtil.isBlank(referer)) {
            referer = "/admin/index";
        }

        redirect(referer);

    }

}
