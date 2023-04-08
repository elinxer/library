# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface

import csv
import os


class MbpromptPipeline:

    def __init__(self):
        # csv文件的位置,无需事先创建
        store_file = os.path.dirname(__file__) + '/spiders/articles.csv'
        print("***************************************************************")
        # 打开(创建)文件

        self.file = open(store_file, 'a+', encoding="utf-8", newline='')
        # csv写法
        self.writer = csv.writer(self.file, dialect="excel")
        self.writer.writerow(["名称", "描述", "图片", "主题ID", "主题名称", "画廊ID"])

    def process_item(self, item, spider):
        # 判断字段值不为空再写入文件
        print("正在写入......")
        if item['name']:
            # 主要是解决存入csv文件时出现的每一个字以‘，’隔离
            self.writer.writerow(
                [item['name'], item['desc'], item['pic'], item['topic_id'], item['topic_name'], item['gallery_id']])
        return item

    def close_spider(self, spider):
        # 关闭爬虫时顺便将文件保存退出
        self.file.close()
