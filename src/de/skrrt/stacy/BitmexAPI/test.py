api_key = 'VjxtSLSdiJYGD_2OOCHOOu2L'
api_secret = 'ee71QLYGhA9HlDUp6CFT6IrhMSDcaWIxLdcQqz6ulermD9GR'

import requests, json
response = requests.get("https://www.bitmex.com/api/v1/orderBook/L2?symbol=xbt&depth=1").json()
ether_ask_price = response[0]['price']
ether_bid_price = response[1]['price']
print(ether_ask_price)
print(ether_bid_price)