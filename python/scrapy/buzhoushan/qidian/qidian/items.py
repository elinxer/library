# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class QidianItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    pass


class QidianBookDetailItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    book_name = scrapy.Field()
    book_url = scrapy.Field()
    book_author = scrapy.Field()
    book_img = scrapy.Field()
    book_intro = scrapy.Field()
    book_tags = scrapy.Field()
    book_status = scrapy.Field()
    pass


class QidianPCHomeBookUrlItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()

    book_url = scrapy.Field()
    pass
