import json
import random

import scrapy
from scrapy import Request

from ..items import LexicaArtItem


class LexicapromptSpider(scrapy.Spider):
    name = 'lexicaPrompt'
    allowed_domains = ['lexica.art']
    start_urls = ['https://lexica.art/api/infinite-prompts']

    UserAgents = [
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36',
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE',
        'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36'
    ]

    custom_settings = {
        'User-Agent': random.choice(UserAgents),
    }
    headers = {'User-Agent': random.choice(UserAgents), 'Content-Type': 'application/json'}

    next_cursor = 1
    page_size = 50
    max_cursor = 10000

    def start_requests(self):
        for url in self.start_urls:
            yield Request(url, headers=self.headers, method="POST", body=json.dumps({
                "text": "",
                "searchMode": "images",
                "source": "search",
                "cursor": self.next_cursor,
                "model": "lexica-aperture-v2"
            }))
        pass

    def parse(self, response):
        print(response.request.headers)
        print(response.request.body)
        responseJson = json.loads(response.text)
        # images = responseJson["images"]
        prompts = responseJson["prompts"]
        print("===> nextCursor : ", responseJson["nextCursor"])
        next_cursor = responseJson["nextCursor"]
        for prompt in prompts:
            # print(prompt)
            item = LexicaArtItem()
            item['id'] = prompt['id']
            item['c'] = prompt['c']
            item['model'] = prompt['model']
            item['images'] = prompt['images']
            item['seed'] = prompt['seed']
            item['height'] = prompt['height']
            item['width'] = prompt['width']
            item['prompt'] = prompt['prompt']
            item['prompt'] = prompt['prompt']
            item['next_cursor'] = next_cursor
            item['negative'] = prompt['negativePrompt']
            yield item
            pass

        # next_cursor 先加
        self.next_cursor += self.page_size
        if self.next_cursor <= self.max_cursor and prompts:
            # print("=========> next_url: ", self.start_urls[0], self.next_cursor)
            # 因为scrapy会去掉重复的链接，所以当请求一次没有获取数据时，想要换个代理ip继续请求要加上dont_filter=True
            yield Request(self.start_urls[0], dont_filter=True, headers=self.headers, method="POST", body=json.dumps({
                "text": "",
                "searchMode": "images",
                "source": "search",
                "cursor": self.next_cursor,
                "model": "lexica-aperture-v2"
            }))
        else:
            print("===> nextCursor : ", self.next_cursor)
        pass
