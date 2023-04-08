import scrapy

from ..items import QidianBookDetailItem

class PcQidianAllSpider(scrapy.Spider):
    name = 'pc_qidian_all'

    allowed_domains = ['qidian.com']
    start_urls = ['http://www.qidian.com/all/']
    load_page_urls = []

    def parse(self, response):

        items = QidianBookDetailItem()

        next_link = response.xpath('//div[@id="page-container"]//a[@class="lbf-pagination-next "]/@href').get()

        for quote in response.xpath('//div[@id="book-img-text"]/ul/li'):
            items["book_name"] = quote.xpath('div[@class="book-mid-info"]/h2/a/text()').get()
            items["book_url"] = quote.xpath('div[@class="book-mid-info"]/h2/a/@href').get()
            items["book_img"] = quote.xpath('div[@class="book-img-box"]/a/img/@src').get()
            items["book_author"] = quote.xpath(
                'div[@class="book-mid-info"]/p[@class="author"]/a[@class="name"]/text()').get()
            items["book_intro"] = quote.xpath('div[@class="book-mid-info"]/p[@class="intro"]/text()').get()

            items["book_url"] = "https:" + items["book_url"]
            yield items

        if next_link:
            if '.com' not in next_link:
                return
            for link in self.load_page_urls:
                if next_link == link:
                    return
                else:
                    self.load_page_urls.append(next_link)
            yield scrapy.Request("https:" + next_link, callback=self.parse, dont_filter=True)
