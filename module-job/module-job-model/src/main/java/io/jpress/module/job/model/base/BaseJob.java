package io.jpress.module.job.model.base;

import com.jfinal.plugin.activerecord.IBean;
import io.jpress.base.BaseOptionsModel;

/**
 * Generated by JPress, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseJob<M extends BaseJob<M>> extends BaseOptionsModel<M> implements IBean {

    private static final long serialVersionUID = 1L;

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return getLong("id");
	}

    /**
     * 职位名称或者标题
     */
	public void setTitle(java.lang.String title) {
		set("title", title);
	}

    /**
     * 职位名称或者标题
     */
	public java.lang.String getTitle() {
		return getStr("title");
	}

    /**
     * 描述
     */
	public void setContent(java.lang.String content) {
		set("content", content);
	}

    /**
     * 描述
     */
	public java.lang.String getContent() {
		return getStr("content");
	}

    /**
     * 对应部门
     */
	public void setDepartment(java.lang.String department) {
		set("department", department);
	}

    /**
     * 对应部门
     */
	public java.lang.String getDepartment() {
		return getStr("department");
	}

    /**
     * 对应分类id
     */
	public void setCategoryId(java.lang.Long categoryId) {
		set("category_id", categoryId);
	}

    /**
     * 对应分类id
     */
	public java.lang.Long getCategoryId() {
		return getLong("category_id");
	}

    /**
     * 工作地点
     */
	public void setAddressId(java.lang.Long addressId) {
		set("address_id", addressId);
	}

    /**
     * 工作地点
     */
	public java.lang.Long getAddressId() {
		return getLong("address_id");
	}

    /**
     * 年龄开始
     */
	public void setAgeLimitStart(java.lang.Integer ageLimitStart) {
		set("age_limit_start", ageLimitStart);
	}

    /**
     * 年龄开始
     */
	public java.lang.Integer getAgeLimitStart() {
		return getInt("age_limit_start");
	}

    /**
     * 年龄结束
     */
	public void setAgeLimitEnd(java.lang.Integer ageLimitEnd) {
		set("age_limit_end", ageLimitEnd);
	}

    /**
     * 年龄结束
     */
	public java.lang.Integer getAgeLimitEnd() {
		return getInt("age_limit_end");
	}

    /**
     * 学历
     */
	public void setEducation(java.lang.Integer education) {
		set("education", education);
	}

    /**
     * 学历
     */
	public java.lang.Integer getEducation() {
		return getInt("education");
	}

    /**
     * 工作年限
     */
	public void setYearsLimitType(java.lang.Integer yearsLimitType) {
		set("years_limit_type", yearsLimitType);
	}

    /**
     * 工作年限
     */
	public java.lang.Integer getYearsLimitType() {
		return getInt("years_limit_type");
	}

    /**
     * 有建立申请时，是否通知
     */
	public void setWithNotify(java.lang.Boolean withNotify) {
		set("with_notify", withNotify);
	}

    /**
     * 有建立申请时，是否通知
     */
	public java.lang.Boolean getWithNotify() {
		return getBoolean("with_notify");
	}

    /**
     * 通知的邮箱
     */
	public void setNotifyEmails(java.lang.String notifyEmails) {
		set("notify_emails", notifyEmails);
	}

    /**
     * 通知的邮箱
     */
	public java.lang.String getNotifyEmails() {
		return getStr("notify_emails");
	}

    /**
     * 通知的邮件标题
     */
	public void setNotifyTitle(java.lang.String notifyTitle) {
		set("notify_title", notifyTitle);
	}

    /**
     * 通知的邮件标题
     */
	public java.lang.String getNotifyTitle() {
		return getStr("notify_title");
	}

    /**
     * 通知的邮件内容
     */
	public void setNotifyContent(java.lang.String notifyContent) {
		set("notify_content", notifyContent);
	}

    /**
     * 通知的邮件内容
     */
	public java.lang.String getNotifyContent() {
		return getStr("notify_content");
	}

    /**
     * 是否属于远程工作
     */
	public void setWithRemote(java.lang.Boolean withRemote) {
		set("with_remote", withRemote);
	}

    /**
     * 是否属于远程工作
     */
	public java.lang.Boolean getWithRemote() {
		return getBoolean("with_remote");
	}

    /**
     * 是否允许在线投递
     */
	public void setWithApply(java.lang.Boolean withApply) {
		set("with_apply", withApply);
	}

    /**
     * 是否允许在线投递
     */
	public java.lang.Boolean getWithApply() {
		return getBoolean("with_apply");
	}

    /**
     * 急招
     */
	public void setWithHurry(java.lang.Boolean withHurry) {
		set("with_hurry", withHurry);
	}

    /**
     * 急招
     */
	public java.lang.Boolean getWithHurry() {
		return getBoolean("with_hurry");
	}

    /**
     * 工作类型
     */
	public void setWorkType(java.lang.Integer workType) {
		set("work_type", workType);
	}

    /**
     * 工作类型
     */
	public java.lang.Integer getWorkType() {
		return getInt("work_type");
	}

    /**
     * 招聘类型
     */
	public void setRecruitType(java.lang.Integer recruitType) {
		set("recruit_type", recruitType);
	}

    /**
     * 招聘类型
     */
	public java.lang.Integer getRecruitType() {
		return getInt("recruit_type");
	}

    /**
     * 岗位招聘人数
     */
	public void setRecruitNumbers(java.lang.Integer recruitNumbers) {
		set("recruit_numbers", recruitNumbers);
	}

    /**
     * 岗位招聘人数
     */
	public java.lang.Integer getRecruitNumbers() {
		return getInt("recruit_numbers");
	}

    /**
     * 岗位有效时间
     */
	public void setExpiredTo(java.util.Date expiredTo) {
		set("expired_to", expiredTo);
	}

    /**
     * 岗位有效时间
     */
	public java.util.Date getExpiredTo() {
		return getDate("expired_to");
	}

	/**
	 * SEO标题
	 */
	public void setMetaTitle(java.lang.String metaTitle) {
		set("meta_title", metaTitle);
	}

	/**
	 * SEO标题
	 */
	public java.lang.String getMetaTitle() {
		return getStr("meta_title");
	}

    /**
     * SEO关键字
     */
	public void setMetaKeywords(java.lang.String metaKeywords) {
		set("meta_keywords", metaKeywords);
	}

    /**
     * SEO关键字
     */
	public java.lang.String getMetaKeywords() {
		return getStr("meta_keywords");
	}

    /**
     * SEO描述信息
     */
	public void setMetaDescription(java.lang.String metaDescription) {
		set("meta_description", metaDescription);
	}

    /**
     * SEO描述信息
     */
	public java.lang.String getMetaDescription() {
		return getStr("meta_description");
	}

    /**
     * 创建日期
     */
	public void setCreated(java.util.Date created) {
		set("created", created);
	}

    /**
     * 创建日期
     */
	public java.util.Date getCreated() {
		return getDate("created");
	}

    /**
     * 最后更新日期
     */
	public void setModified(java.util.Date modified) {
		set("modified", modified);
	}

    /**
     * 最后更新日期
     */
	public java.util.Date getModified() {
		return getDate("modified");
	}

    /**
     * 站点ID
     */
	public void setSiteId(java.lang.Long siteId) {
		set("site_id", siteId);
	}

    /**
     * 站点ID
     */
	public java.lang.Long getSiteId() {
		return getLong("site_id");
	}

}

