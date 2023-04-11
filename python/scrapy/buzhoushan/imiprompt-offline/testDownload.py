import os

import requests

# 图片链接
image_url1 = "https://www.imiprompt.com/_filters/anatomical-drawing.png"
storage_path1 = "images/"


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


# 下载图片
download_image(image_url1, storage_path1)
