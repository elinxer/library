import random

import scrapy
from scrapy import FormRequest

from ..items import MbpromptItem


class MbpromptspiderSpider(scrapy.Spider):
    name = 'mbpromptSpider'
    allowed_domains = ['mbprompt.com']
    start_urls = ['https://www.mbprompt.com/wp-admin/admin-ajax.php?action=bwg_frontend_data']

    UserAgents = [
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE',
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE',
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE']
    UserAgent = random.choice(UserAgents)
    headers = {'User-Agent': UserAgent, 'Content-Type': 'application/x-www-form-urlencoded'}

    topic = ["主题", "风格", "艺术家", "镜头", "技巧", "材质", "光线", "布局", "建筑", "时代", "电影&游戏", "人物",
             "时装", "背景", "构图", "国风", "情绪", "暂定", "暂定"]

    topic_ids = ["44", "46", "43", "47", "48", "49", "50", "51", "52", "53", "54", "60",
                 "57", "58", "63", "64", "情绪", "暂定", "暂定"]

    gallery_ids = ["7", "6", "3", "9", "5", "8", "10", "4", "12", "11", "13", "16",
                   "14", "15", "18", "17", "情绪", "暂定", "暂定"]

    # current = 0
    # current = 1
    # current = 2
    current = 3
    # current = 4
    # current = 5
    # current = 6
    # current = 7
    # current = 8
    # current = 9
    # current = 10
    # current = 11
    # current = 12
    # current = 13
    # current = 14
    # current = 15
    # current = 16

    topic_id = topic_ids[current]
    topic_name = topic[current]
    gallery_id = gallery_ids[current]

    page = 1

    def start_requests(self):
        for url in self.topic:
            url = self.start_urls[0]
            yield FormRequest(url, headers=self.headers, dont_filter=True, method="POST", formdata={
                "bwg-preview-type": "thumbnails",
                "gallery_type": "thumbnails",
                "gallery_id": self.gallery_id,
                "tag": "0",
                "album_id": "0",
                "theme_id": "3",
                "shortcode_id": self.topic_id,
                "bwg": "0",
                "current_url": "/?",
                "page_number_0": "1",
                "bwg_load_more_0": "on",
                "album_gallery_id_0": "0",
                "type_0": "gallery",
                "title_0": "",
                "description_0": "",
                "sortImagesByValue_0": "default",
                "bwg_random_seed_0": "1809035866",
                "bwg_search_0": ""
            })

    def parse(self, response):
        # print(response.request.headers)
        subSelector = response.xpath('.//div[@class="bwg-item"]')
        items = []
        for sub in subSelector:
            item = MbpromptItem()
            item['name'] = sub.xpath('.//div[@class="bwg-title2"]/text()').extract_first()
            item['desc'] = sub.xpath('.//span/text()').extract_first()
            if not item['desc']:
                item['desc'] = item['name']
            item['pic'] = sub.xpath('./a/@href').extract_first()
            item["topic_name"] = self.topic_name
            item["topic_id"] = self.topic_id
            item["gallery_id"] = self.gallery_id
            items.append(item)
            yield item

        self.page += 1
        if self.page >= 20:
            pass
        else:
            # next_url = response.xpath('//div//a[@class="bwg_load_btn"]/@href').extract_first()
            next_url = response.xpath('.//span//a[@class="bwg-a bwg_load_btn_0 bwg_load_btn"]/text()').extract_first()
            print("next_url: ", next_url, self.page)
            if next_url and next_url != "javascript:;" and "裝載更多" in next_url:
                next_url = self.start_urls[0]
                # 因为scrapy会去掉重复的链接，所以当请求一次没有获取数据时，想要换个代理ip继续请求要加上dont_filter=True
                yield FormRequest(next_url, headers=self.headers, method="POST", dont_filter=True, formdata={
                    "bwg-preview-type": "thumbnails",
                    "gallery_type": "thumbnails",
                    "gallery_id": self.gallery_id,
                    "tag": "0",
                    "album_id": "0",
                    "theme_id": "3",
                    "shortcode_id": self.topic_id,
                    "bwg": "0",
                    "current_url": "/?",
                    "page_number_0": "" + str(self.page),
                    "bwg_load_more_0": "on",
                    "album_gallery_id_0": "0",
                    "type_0": "gallery",
                    "title_0": "",
                    "description_0": "",
                    "sortImagesByValue_0": "default",
                    "bwg_random_seed_0": "1809035866",
                    "bwg_search_0": ""
                })
            pass

# if __name__ == '__main__':
#     from scrapy import cmdline
#     cmdline.execute("scrapy crawl mbpromptSpider".split())
