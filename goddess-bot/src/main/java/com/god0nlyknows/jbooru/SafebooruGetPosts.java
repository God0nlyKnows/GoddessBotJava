package com.god0nlyknows.jbooru;

import java.util.List;

import com.god0nlyknows.jbooru.dto.IResponseDTO;
import com.god0nlyknows.jbooru.dto.SafebooruResponseDTO;

public class SafebooruGetPosts extends GetPostsBase implements IGetPosts {
    private int limit = 1000; // max per request
    private int page = 0;

    @Override
    public List<IResponseDTO> getPosts(String tag) {
        if (tag != null)
            return sendRequest(String.format(
                    "https://safebooru.org/index.php?page=dapi&s=post&q=index&json=1&limit=%s&pid=%s&tags=%s", limit,
                    page, tag), SafebooruResponseDTO[].class);
        return sendRequest(String.format(
                "https://safebooru.org/index.php?page=dapi&s=post&q=index&json=1&limit=%s&pid=%s", limit,
                page), SafebooruResponseDTO[].class);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
