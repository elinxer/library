import scrapy
import json

from ..items import XbiqugeItem


class PcRankTopSpider(scrapy.Spider):
    name = 'pc_rank_top'
    allowed_domains = ['xbiquge.so']
    start_urls = ['https://www.xbiquge.so/top/allvisit/600.html']

    unique_urls = []

    def parse(self, response):
        items = XbiqugeItem()

        content = response.css('.novelslistss')
        next_link = response.css("#pagelink .next::attr(href)").get()
        for detail in content.css("li"):
            items["book_name"] = detail.css(".s2 a::text").get()
            items["book_author"] = detail.css(".s4::text").get()
            items["book_url"] = detail.css(".s2 a::attr(href)").get()
            items["book_lasted"] = detail.css(".s5::text").get()
            yield items
        if next_link:
            yield scrapy.Request(next_link, callback=self.parse, dont_filter=True)
        pass
