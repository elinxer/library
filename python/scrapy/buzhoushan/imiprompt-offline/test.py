import os

import requests
from lxml import etree


def parse_local(file):
    content = etree.parse(file, etree.HTMLParser())
    response = content.xpath(
        '//div[@class="dark:bg-[#131624] bg-gray-200 rounded-md overflow-hidden cursor-pointer select-none"]')

    itemArr = []
    for html in response:
        arr = {}

        arr['pic'] = html.xpath(".//img[@class='w-full dark:opacity-80 opacity-90']/@src")
        arr['name'] = html.xpath(".//div[@class='py-1 px-2 text-sm text-gray-500']/text()")

        arr['pic'] = "https://www.imiprompt.com" + arr['pic'][0]
        itemArr.append(arr)

    print(itemArr)
    pass


def download_image(image_url, storage_path):
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36'
    }
    img_path = image_url.split('/')[-1]
    img_prefix = img_path.split('.')[-1]
    img_name = img_path.split('.')[-2]
    print(img_path, img_name, img_prefix)
    new_path = storage_path + img_name + "." + img_prefix
    if not os.path.exists(storage_path):
        os.mkdir(storage_path)
    r = requests.get(image_url, headers=headers)
    # download image
    with open(new_path, mode="wb") as f:
        f.write(r.content)
    print("====> download success: " + new_path)
    pass


local_file = "file:///C:/Users/Administrator/Desktop/imi-layouts.html"

parse_local(local_file)
