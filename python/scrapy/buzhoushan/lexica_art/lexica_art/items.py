# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class LexicaArtItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()

    id = scrapy.Field()
    c = scrapy.Field()
    model = scrapy.Field()
    images = scrapy.Field()
    seed = scrapy.Field()
    height = scrapy.Field()
    width = scrapy.Field()
    prompt = scrapy.Field()
    next_cursor = scrapy.Field()

    pass
