import scrapy

from ..items import MbpromptItem


class MbpromptspiderSpider(scrapy.Spider):
    name = 'mbpromptSpider'
    allowed_domains = ['mbprompt.com']
    start_urls = ['https://www.mbprompt.com/wp-admin/admin-ajax.php?action=bwg_frontend_data']

    custom_settings = {
        ':authority': 'www.mbprompt.com',
        ':scheme': 'https',
        'accept-encoding': 'gzip, deflate, br',
        'accept-language': 'zh-CN,zh;q=0.9',
        'cache-control': 'max-age=0',
        'sec-ch-ua': '"Google Chrome";v="93", " Not;A Brand";v="99", "Chromium";v="93"',
        'sec-ch-ua-mobile': '?0',
        'sec-ch-ua-platform': '"macOS"',
        'sec-fetch-dest': 'document',
        'sec-fetch-mode': 'navigate',
        # 'sec-fetch-site': 'none',
        'referer': 'https://mbprompt.com'
    }

    def parse(self, response):
        print(response)
        subSelector = response.xpath('.//div[@class="bwg-item"]')
        items = []
        for sub in subSelector:
            item = MbpromptItem()
            item['name'] = sub.xpath('.//div[@class="bwg-title2"]/text()').extract()[0]
            desc = sub.xpath('.//span/text()').extract()
            if desc:
                item['desc'] = desc[0]
            else:
                item['desc'] = item['name']

            item['picUrl'] = sub.xpath('./a/@href').extract()[0]
            items.append(item)
            print(item)
        pass

#
# if __name__ == '__main__':
#     from scrapy import cmdline
#     cmdline.execute("scrapy crawl mbpromptSpider".split())
