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

    page = 1

    def start_requests(self):

        for url in self.start_urls:
            yield FormRequest(url, headers=self.headers, method="POST", formdata={
                "bwg-preview-type": "thumbnails",
                "gallery_type": "thumbnails",
                "gallery_id": "7",
                "tag": "0",
                "album_id": "0",
                "theme_id": "3",
                "shortcode_id": "44",
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
        print(response.request.headers)
        subSelector = response.xpath('.//div[@class="bwg-item"]')
        items = []
        for sub in subSelector:
            item = MbpromptItem()
            item['name'] = sub.xpath('.//div[@class="bwg-title2"]/text()').extract_first()
            desc = sub.xpath('.//span/text()').extract_first()
            if not desc:
                item['desc'] = item['name']
            item['picUrl'] = sub.xpath('./a/@href').extract_first()
            items.append(item)
            print(item)

        self.page += 1
        if self.page >= 20:
            pass
        else:
            # next_url = response.xpath('//div//a[@class="bwg_load_btn"]/@href').extract_first()
            next_url = response.xpath('.//span//a[@class="bwg-a bwg_load_btn_0 bwg_load_btn"]/text()').extract_first()
            print("next_url: ", next_url, self.page)
            if next_url and next_url != "javascript:;":
                next_url = self.start_urls[0]
                yield FormRequest(next_url, headers=self.headers, method="POST", dont_filter=True, formdata={
                    "bwg-preview-type": "thumbnails",
                    "gallery_type": "thumbnails",
                    "gallery_id": "7",
                    "tag": "0",
                    "album_id": "0",
                    "theme_id": "3",
                    "shortcode_id": "44",
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
