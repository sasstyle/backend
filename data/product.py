import requests

URL = "http://203.252.240.42:8000/product-service/products"
token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzYXNzdHlsZS5jb20iLCJleHAiOjE2NTgzNTE5MzcsInVzZXJJZCI6ImFmZGFiYmU5LTU1NGItNDdjMi1iZDhlLTI0OGY0MDE4NjFjNCIsInVzZXJuYW1lIjoic2Fzc3R5bGUifQ.NEeXV8bPNd2XXHQ6tZT3JluXHsAQpl-KgV3Ovjchh_x_tEurzGbMm8vPMFmQG5vN_EGt79e5T17NZwao4fjTfA"
headers={'Authorization': token}

requests.post(URL, json={
    "categoryId": 6,
    "profileUrl": "https://image.msscdn.net/images/goods_img/20200910/1595528/1595528_4_500.jpg?t=20220106221121",
    "name": "(22ALL) (BY P.E.DEPT) UNIVERSITY HANDSOME DAN CREWNECK NAVY",
    "price": 36000,
    "stockQuantity": 100,
    "images": ["https://cdn001.negagea.net/yalerecords/yale/01.06/yale_intro.jpg",
    "https://cdn001.negagea.net/yalerecords/yale/01.06/YB7CR0011NA/5.jpg",
    "https://cdn001.negagea.net/yalerecords/yale/01.06/YB7CR0011NA/6.jpg",
    "https://cdn001.negagea.net/yalerecords/yale/01.06/YB7CR0011NA/7.jpg"]
}, headers=headers)


requests.post(URL, json={
    "categoryId": 6,
    "profileUrl": "https://image.msscdn.net/images/goods_img/20200820/1557508/1557508_3_500.jpg?t=20211110110947",
    "name": "베츠 어센틱 맨투맨 그레이",
    "price": 41300,
    "stockQuantity": 100,
    "images": ["https://ebbets.cafe24.com/21FW/FREEORDER/fixintro.jpg",
    "https://ebbets.cafe24.com/21FW/03/00.jpg",
    "https://ebbets.cafe24.com/21FW/03/ct.jpg",
    "https://ebbets.cafe24.com/21FW/03/betsman/gr/01.jpg",
    "https://ebbets.cafe24.com/21FW/03/betsman/02.jpg"]
}, headers=headers)

requests.post(URL, json={
    "categoryId": 6,
    "profileUrl": "https://image.msscdn.net/images/goods_img/20190218/956939/956939_3_500.jpg?t=20211126151435",
    "name": "1960 sweatshirts navy",
    "price": 36000,
    "stockQuantity": 100,
    "images": ["https://image.musinsa.com/images/prd_img/2022020814075700000046139.jpg",
    "https://image.musinsa.com/images/prd_img/2020022113443600000080474.jpg"]
}, headers=headers)