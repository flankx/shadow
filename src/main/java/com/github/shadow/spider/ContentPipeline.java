package com.github.shadow.spider;

import com.github.shadow.entity.ZhihuContent;
import com.github.shadow.service.IZhihuContentService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Slf4j
@AllArgsConstructor
public class ContentPipeline implements Pipeline {

    private IZhihuContentService contentService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        ZhihuContent content = new ZhihuContent();
        content.setTitle(resultItems.get("title"));
        content.setQuestion(resultItems.get("question"));
        content.setContent(resultItems.get("answer"));
        try {
            if (contentService.save(content)) {
                log.info("Content saved successfully! title: " + content.getTitle());
            } else {
                log.error("Content saved failed! title: " + content.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
