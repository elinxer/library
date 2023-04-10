import scrapy


class NovelaitagsSpider(scrapy.Spider):
    name = "novelaiTags"
    allowed_domains = ["tags.novelai.dev"]
    start_urls = ["http://tags.novelai.dev/"]

    def parse(self, response):
        pass
