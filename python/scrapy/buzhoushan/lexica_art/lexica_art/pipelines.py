# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


import csv
# useful for handling different item types with a single interface
import os
import random

import pymysql
import requests


class LexicaArtPipeline:

    def __init__(self):
        # csv文件的位置,无需事先创建
        store_file = os.path.dirname(__file__) + '/../prompts.csv'
        print("***************************************************************")
        # 打开(创建)文件
        self.file = open(store_file, 'a+', encoding="utf-8", newline='')
        # csv写法
        self.writer = csv.writer(self.file, dialect="excel")
        self.writer.writerow(
            ["id", "cat", "model", "seed", "height", "width", 'prompt', "next_cursor", "image_id", "upscaled_height",
             "upscaled_width", "image_height", "image_width", "negative_prompt"])

        self.db = pymysql.connect(host="47.119.145.8", user="root", passwd="#p?Z#x+eq4jp", db="test", charset="utf8")
        self.cursor = self.db.cursor()

    def download_image(self, image_url, storage_path):
        UserAgents = [
            'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36',
            'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE',
            'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36'
        ]
        headers = {
            'User-Agent': random.choice(UserAgents)
        }
        img_path = image_url.split('/')[-1]
        img_prefix = "webp"
        img_name = image_url.split('/')[-1]
        # print(img_path, img_name, img_prefix)
        new_path = storage_path + img_name + "." + img_prefix
        if not os.path.exists(storage_path):
            os.mkdir(storage_path)
        if os.path.exists(new_path):
            print("====> download error exists: " + new_path)
            pass
        else:
            r = requests.get(image_url, headers=headers)
            # download image
            with open(new_path, mode="wb") as f:
                f.write(r.content)
            # print("====> download success: " + new_path)
            pass

    def process_item(self, item, spider):
        # print("================ 正在写入API...... ================")
        # 图片下载
        for image in item['images']:
            prompt = item['prompt'].replace('\r', '').replace('\n', '').replace('\t', '')
            negative = item['negative'].replace('\r', '').replace('\n', '').replace('\t', '')

            save_item = [
                item['id'], item['c'], item['model'], item['seed'], item['height'], item['width'], prompt,
                item['next_cursor'], image['id'], image['upscaled_height'], image['upscaled_width'],
                image['height'], image['width'], negative
            ]

            self.writer.writerow(save_item)

            self.mysql_save(save_item)

            image_url = "https://image.lexica.art/md2/" + image['id']
            # START DOWNLOAD
            # self.download_image(image_url, "md2/")
            pass
        return item

    def close_spider(self, spider):
        # 关闭爬虫时顺便将文件保存退出
        self.file.close()
        self.cursor.close()
        self.db.close()

    def mysql_save(self, save_item):
        self.cursor.execute(
            "INSERT IGNORE INTO `test`.`library_prompt_lexica`(`guid`, `c`, `model`, `seed`, `height`, `width`, `prompt`, `next_cursor`, `image_id`, `upscaled_height`, `upscaled_width`, `image_height`, `image_width`, `negative_prompt`) "
            + " VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
            save_item
        )
        self.db.commit()
        pass
