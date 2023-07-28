import os
import fitz

"""
# 命令行安装依赖（必须先安装fitz，依靠安装PyMuPDF刷新全部依赖）

# python -m pip install fitz -i https://pypi.tuna.tsinghua.edu.cn/simple
# python -m pip install PyMuPDF -i https://pypi.tuna.tsinghua.edu.cn/simple
# python -m pip install traits -i https://pypi.tuna.tsinghua.edu.cn/simple
#
# 简化
# pip install fitz
# pip install pymupdf

"""

from flask import Flask
# 实例化，可视为固定格式
app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello, World!'


@app.route('/pdf')
def pdf():
    pdf_dir = get_file()
    convert_img(pdf_dir)
    return 'success'


def get_file():
    files = os.listdir()  # 默认访问的是当前路径
    lis = [file for file in files if os.path.splitext(file)[1] == '.pdf']
    return lis


def convert_img(pdf_dirs):
    for pdf in pdf_dirs:
        doc = fitz.open(pdf)
        pdf_name = os.path.splitext(pdf)[0]

        for pg in range(doc.page_count):
            page = doc[pg]
            rotate = int(0)
            # 每个尺寸的缩放系数为10，这将为我们生成分辨率提高100倍的图像。
            zoom_x, zoom_y = 10, 10
            trans = fitz.Matrix(zoom_x, zoom_y).prerotate(rotate)
            pm = page.get_pixmap(matrix=trans, alpha=False)
            pm.save('%s.png' % pdf_name)
            # 只取第一张
            return


if __name__ == '__main__':
    # 获取当前路径所有pdf
    app.run(host="0.0.0.0", port=5000)

