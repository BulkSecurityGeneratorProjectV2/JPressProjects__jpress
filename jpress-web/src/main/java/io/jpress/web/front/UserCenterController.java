/**
 * Copyright (c) 2016-2019, Michael Yang 杨福海 (fuhai999@gmail.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.web.front;

import com.jfinal.aop.Aop;
import com.jfinal.aop.Inject;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import io.jboot.utils.CookieUtil;
import io.jboot.utils.FileUtil;
import io.jboot.utils.StrUtil;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jboot.web.validate.EmptyValidate;
import io.jboot.web.validate.Form;
import io.jpress.JPressConfig;
import io.jpress.JPressConsts;
import io.jpress.commons.utils.AliyunOssUtils;
import io.jpress.commons.utils.AttachmentUtils;
import io.jpress.commons.utils.ImageUtils;
import io.jpress.model.*;
import io.jpress.service.PaymentRecordService;
import io.jpress.service.UserCartService;
import io.jpress.service.UserService;
import io.jpress.web.base.UcenterControllerBase;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Yang 杨福海 （fuhai999@gmail.com）
 * @version V1.0
 * @Package io.jpress.web
 */
@RequestMapping(value = "/ucenter", viewPath = "/WEB-INF/views/ucenter/")
public class UserCenterController extends UcenterControllerBase {

    @Inject
    private UserService userService;

    @Inject
    private UserCartService cartService;

    /**
     * 用户中心首页
     */
    public void index() {
        render("index.html");
    }


    public void info() {
        render("info.html");
    }

    public void doSaveUser() {
        User user = getBean(User.class);
        user.keepUpdateSafe();
        user.setId(getLoginedUser().getId());

        //若用户更新邮件，那么重置邮件状态为：未激活
        if (user.getEmail() != null
                && user.getEmail().equals(getLoginedUser().getEmail()) == false) {
            user.setEmailStatus(null);
        }

        userService.saveOrUpdate(user);
        renderOkJson();
    }


    /**
     * 个人签名
     */
    public void signature() {
        render("signature.html");
    }


    /**
     * 头像设置
     */
    public void avatar() {
        render("avatar.html");
    }


    /**
     * 账号密码
     */
    public void pwd() {
        render("pwd.html");
    }


    /**
     * 购物车
     */
    public void cart() {
        Page<UserCart> page = cartService.paginate(1, 10);
        setAttr("page", page);

        render("cart.html");
    }

    /**
     * 购买页面
     */
    public void checkout() {
        List<UserCart> userCarts = new ArrayList<>();
        Long cid = getParaToLong();
        if (cid != null) {
            userCarts.add(cartService.findById(cid));
        } else {
            userCarts.addAll(cartService.findSelectedByUserId(getLoginedUser().getId()));
        }

        setAttr("userCarts", userCarts);
        render("checkout.html");
    }

    /**
     * 开始购买
     */
    public void doCheckout() {
        UserOrder userOrder = new UserOrder();
        userOrder.setBuyerId(getLoginedUser().getId());
        userOrder.setBuyerMsg(getPara("buyer_msg"));
        userOrder.setBuyerNickname(getLoginedUser().getNickname());

        UserOrderItem userOrderItem = new UserOrderItem();



        PaymentRecord payment = new PaymentRecord();
        payment.setProductName("用户充值");
        payment.setProductType("recharge");

        payment.setTrxNo(StrUtil.uuid());
        payment.setTrxType("recharge");

        payment.setPayerUserId(getLoginedUser().getId());
        payment.setPayerName(getLoginedUser().getNickname());
        payment.setPayerFee(BigDecimal.ZERO);

        payment.setOrderIp(getIPAddress());
        payment.setOrderRefererUrl(getReferer());

        payment.setPayAmount(BigDecimal.valueOf(Long.valueOf(getPara("recharge_amount"))));
        payment.setStatus("1");

        PaymentRecordService paymentService = Aop.get(PaymentRecordService.class);
        paymentService.save(payment);


        PayKit.redirect(getPara("paytype"),payment.getTrxNo());
    }


    @EmptyValidate({
            @Form(name = "oldPwd", message = "旧不能为空"),
            @Form(name = "newPwd", message = "新密码不能为空"),
            @Form(name = "confirmPwd", message = "确认密码不能为空")
    })
    public void doUpdatePwd(String oldPwd, String newPwd, String confirmPwd) {

        User user = getLoginedUser();

        if (userService.doValidateUserPwd(user, oldPwd).isFail()) {
            renderJson(Ret.fail().set("message", "密码错误"));
            return;
        }

        if (newPwd.equals(confirmPwd) == false) {
            renderJson(Ret.fail().set("message", "两次出入密码不一致"));
            return;
        }

        String salt = user.getSalt();
        String hashedPass = HashKit.sha256(salt + newPwd);

        user.setPassword(hashedPass);
        userService.update(user);

        renderOkJson();
    }


    @EmptyValidate({
            @Form(name = "path", message = "请先选择图片")
    })
    public void doSaveAvatar(String path, int x, int y, int w, int h) {

        String attachmentRoot = JPressConfig.me.getAttachmentRootOrWebRoot();

        String oldPath = attachmentRoot + path;

        //先进行图片缩放，保证图片和html的图片显示大小一致
        String zoomPath = AttachmentUtils.newAttachemnetFile(FileUtil.getSuffix(path)).getAbsolutePath();
        ImageUtils.zoom(500, oldPath, zoomPath); //500的值必须和 html图片的max-width值一样

        //进行剪切
        String newAvatarPath = AttachmentUtils.newAttachemnetFile(FileUtil.getSuffix(path)).getAbsolutePath();
        ImageUtils.crop(zoomPath, newAvatarPath, x, y, w, h);

        String newPath = FileUtil.removePrefix(newAvatarPath, attachmentRoot).replace("\\", "/");

        AliyunOssUtils.upload(newPath, new File(newAvatarPath));

        User loginedUser = getLoginedUser();
        loginedUser.setAvatar(newPath);
        userService.saveOrUpdate(loginedUser);
        renderOkJson();
    }

    /**
     * 退出登录
     */
    public void doLogout() {
        CookieUtil.remove(this, JPressConsts.COOKIE_UID);
        redirect("/user/login");
    }


}
