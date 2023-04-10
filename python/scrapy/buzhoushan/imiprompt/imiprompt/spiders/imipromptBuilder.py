import random

import scrapy

from ..items import ImipromptItem


class ImipromptbuilderSpider(scrapy.Spider):
    name = "imipromptBuilder"
    allowed_domains = ["imiprompt.com"]
    start_urls = ["https://www.imiprompt.com/builder"]

    UserAgents = [
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE',
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE',
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE']
    UserAgent = random.choice(UserAgents)
    headers = {'User-Agent': UserAgent, 'Content-Type': 'application/x-www-form-urlencoded'}

    custom_settings = {
        'User-Agent': UserAgent,
    }

    def parse(self, response):

        print(response)
        subSelector = response.xpath('.//div[@class="light"]/text()')
        print(subSelector)
        items = []
        for sub in subSelector:
            item = ImipromptItem()

            print(sub.xpath("//text()"))

            items.append(item)
            yield item

        pass
