package io.jpress.module.product.model;

import io.jboot.db.annotation.Table;
import io.jboot.utils.StrUtil;
import io.jpress.commons.utils.CommonsUtils;
import io.jpress.commons.utils.JsoupUtils;
import io.jpress.commons.utils.UrlUtils;
import io.jpress.module.product.model.base.BaseProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JPress.
 */
@Table(tableName = "product", primaryKey = "id")
public class Product extends BaseProduct<Product> {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_DRAFT = 2;
    public static final int STATUS_TRASH = 3;


    public boolean isNormal() {
        return getStatus() != null && getStatus() == STATUS_NORMAL;
    }

    public boolean isDraft() {
        return getStatus() != null && getStatus() == STATUS_DRAFT;
    }

    public boolean isTrash() {
        return getStatus() != null && getStatus() == STATUS_TRASH;
    }


    public String getUrl() {
        return UrlUtils.getUrl("/product/", StrUtil.isNotBlank(getSlug()) ? getSlug() : getId());
    }


    public String getUrlWithPageNumber(int pageNumber) {
        if (pageNumber <= 1) {
            return getUrl();
        }
        return UrlUtils.getUrl("/product/", StrUtil.isNotBlank(getSlug()) ? getSlug() : getId(), "-", pageNumber);
    }

    public String getHtmlView() {
        return StrUtil.isBlank(getStyle()) ? "product.html" : "product_" + getStyle().trim() + ".html";
    }

    public String getText() {
        return JsoupUtils.getText(getContent());
    }

    /**
     * 获取文章的所有图片
     *
     * @return
     */
    public List<String> getImages() {
        return JsoupUtils.getImageSrcs(getContent());
    }

    /**
     * 获取前面几张图片
     *
     * @param count
     * @return
     */
    public List<String> getImages(int count) {
        List<String> list = getImages();
        if (list == null || list.size() <= count) {
            return list;
        }

        List<String> newList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public boolean hasImage() {
        return getFirstImage() != null;
    }

    public boolean hasVideo() {
        return getFirstVideo() != null;
    }

    public boolean hasAudio() {
        return getFirstAudio() != null;
    }

    public String getFirstImage() {
        return JsoupUtils.getFirstImageSrc(getContent());
    }

    public String getFirstVideo() {
        return JsoupUtils.getFirstVideoSrc(getContent());
    }

    public String getFirstAudio() {
        return JsoupUtils.getFirstAudioSrc(getContent());
    }

    public String getShowImage() {
        String thumbnail = getThumbnail();
        return StrUtil.isNotBlank(thumbnail) ? thumbnail : getFirstImage();
    }

    public boolean isCommentEnable() {
        Boolean cs = getCommentStatus();
        return cs != null && cs == true;
    }



    public String getHighlightContent() {
        String content = getStr("highlightContent");
        return StrUtil.isNotBlank(content) ? content : CommonsUtils.maxLength(getText(), 100, "...");
    }

    public void setHighlightContent(String highlightContent) {
        put("highlightContent", highlightContent);
    }

    public String getHighlightTitle() {
        String title = getStr("highlightTitle");
        return StrUtil.isNotBlank(title) ? title : getTitle();
    }

    public void setHighlightTitle(String highlightTitle) {
        put("highlightTitle", highlightTitle);
    }

}