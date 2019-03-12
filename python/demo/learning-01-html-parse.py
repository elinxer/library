from htmldom import htmldom

dom = htmldom.HtmlDom("https://www.baidu.com").createDom()
# Find all the links present on a page and prints its "href" value
a = dom.find("a")
for link in a:
    print(link.attr("href"))
