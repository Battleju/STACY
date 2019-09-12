import threading

import requests, json
import time
import traceback
from math import ceil, log10
from bitmex import bitmex
from threading import Thread
# Testnet
# id: XgJgE15rA1qsDxsQ8CIXTPHd
# secret: 4rDo-sVCMzUAnXmWOmEud7qMgAZn-ANBfRdFfz1lClDHIdue

class API:

    global api_key
    global api_secret
    global client

    def __init__(self):
        print("Start Api...")
        try:
            filekey = open("src/de/skrrt/stacy/BitmexAPI/key.key", "r")
            api_key = filekey.read().strip()
            api_secret = filekey.read().strip()
            print(api_key)
            print(api_secret)
            client = bitmex(test=False, api_key=api_key, api_secret=api_secret)
        except FileNotFoundError:
            #print("TestNET Started")
            api_key = 'XgJgE15rA1qsDxsQ8CIXTPHd'
            api_secret = '4rDo-sVCMzUAnXmWOmEud7qMgAZn-ANBfRdFfz1lClDHIdue'
            client = bitmex(test=True, api_key=api_key, api_secret=api_secret)

        # self.testbuy(client=client)
        # self.testpos(client)
        # self.testopenorder(client)
        # self.testcancelOrder(client)



        def updatePrices(running=True):
            while running:
                time.sleep(4)
                try:
                    xbt = requests.get("https://www.bitmex.com/api/v1/orderBook/L2?symbol=xbt&depth=1").json()
                    eth = requests.get("https://www.bitmex.com/api/v1/orderBook/L2?symbol=eth&depth=1").json()
                    xrp = requests.get("https://www.bitmex.com/api/v1/orderBook/L2?symbol=xrp&depth=1").json()
                    print(xbt[0]['price'])
                    print(format(xrp[0]['price'], '.8f'))
                    print(eth[0]['price'])
                    file1 = open("src/de/skrrt/stacy/BitmexAPI/xbt.txt", "w")
                    file1.write(str(xbt[0]['price']))
                    file1.close()
                    file2 = open("src/de/skrrt/stacy/BitmexAPI/xrp.txt", "w")
                    file2.write(str(format(xrp[0]['price'], '.8f')))
                    file2.close()
                    file3 = open("src/de/skrrt/stacy/BitmexAPI/eth.txt", "w")
                    file3.write(str(eth[0]['price']))
                    file3.close()

                except KeyError:
                    print("Rate Limit exceeded")
                    time.sleep(10)

        def updatePos(running=True):
            while running:
                time.sleep(5)
                try:
                    pos = client.Position.Position_get().result()

                    x = len(pos[0])
                    i = 0
                    file = open("src/de/skrrt/stacy/BitmexAPI/position.txt", "w")
                    while i < x:
                        file.write(str(pos[0][i]['symbol']))
                        file.write('\n')
                        file.write(str(pos[0][i]['leverage']))
                        file.write('\n')
                        file.write(str(pos[0][i]['currentQty']))
                        file.write('\n')
                        file.write(str(format(pos[0][i]['markPrice'], '.8f')))
                        file.write('\n')
                        file.write(str(format(pos[0][i]['liquidationPrice'], '.8f')))
                        file.write('\n')
                        i = i + 1
                except KeyError:
                    print("Rate Limit exceeded")
                    time.sleep(10)
                file.close()

                time.sleep(5)
                try:
                    file = open("src/de/skrrt/stacy/BitmexAPI/openOrders.txt", "w")
                    pos = client.Order.Order_getOrders(filter="{\"open\": true}").result()
                    x = len(pos[0])
                    i = 0
                    while i < x:
                        file.write(str(pos[0][i]['symbol']))
                        file.write('\n')
                        file.write(str(pos[0][i]['ordType']))
                        file.write('\n')
                        file.write(str(pos[0][i]['orderID']))
                        file.write('\n')
                        file.write(str(format(pos[0][i]['stopPx'], '.8f')))
                        file.write('\n')
                        file.write('-')
                        file.write('\n')
                        i = i + 1
                except KeyError:
                    print("Rate Limit exceeded")
                    time.sleep(10)
                file.close()

        def check4CloseOrder(running=True):
            while running:
                time.sleep(0.1)
                print("hi")
                file = open("src/de/skrrt/stacy/BitmexAPI/order.txt", "r")
                line = file.readline()
                file.close()
                if line.strip() != "none":
                    try:
                        client.Order.Order_cancel(orderID=line.strip()).result()
                    except:
                        print(traceback.format_exc())
                file = open("src/de/skrrt/stacy/BitmexAPI/order.txt", "w")
                file.write("none")


        def check4orders(running=True):
            while running:
                time.sleep(0.1)
                file = open("src/de/skrrt/stacy/BitmexAPI/order.txt", "r")
                lines = file.readlines()
                file.close()
                if lines[0] != "none":
                    ordertype = lines[0]
                    orderside = lines[1]
                    asset = lines[2]
                    leverage = lines[3]
                    quantity = lines[4]
                    stoploss = lines[5]
                    limitPrice = lines[6]
                    triggerPrice = lines[7]
                    takeProfit = lines[8]

                    ordertype = ordertype.strip()
                    orderside = orderside.strip()
                    asset = asset.strip()
                    leverage = leverage.strip()
                    quantity = quantity.strip()
                    stoploss = stoploss.strip()
                    limitPrice = limitPrice.strip()
                    triggerPrice = triggerPrice.strip()
                    takeProfit = takeProfit.strip()
                    try:
                        if(ordertype == "MARKET"):
                            if(asset == "BTC"):
                                client.Position.Position_updateLeverage(symbol='XBTUSD', leverage=leverage).result()
                                if(orderside == "LONG"):
                                    client.Order.Order_new(symbol='XBTUSD', orderQty=quantity, ordType="Market",
                                                           side= 'Buy').result()
                                else:
                                    client.Order.Order_new(symbol='XBTUSD', orderQty=quantity, ordType="Market",
                                                           side='Sell').result()

                            if (asset == "ETH"):
                                client.Position.Position_updateLeverage(symbol='ETHUSD', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='ETHUSD', orderQty=quantity, ordType="Market",
                                                           side='Buy').result()
                                else:
                                    client.Order.Order_new(symbol='ETHUSD', orderQty=quantity, ordType="Market",
                                                           side='Sell').result()

                            if (asset == "XRP"):
                                client.Position.Position_updateLeverage(symbol='XRPU19', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='XRPU19', orderQty=quantity, ordType="Market",
                                                           side='Buy').result()
                                else:
                                    client.Order.Order_new(symbol='XRPU19', orderQty=quantity, ordType="Market",
                                                           side='Sell').result()

                        if(ordertype == "LIMIT"):
                            if (asset == "BTC"):
                                client.Position.Position_updateLeverage(symbol='XBTUSD', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='XBTUSD', orderQty=quantity, ordType="Limit",
                                                           side='Buy', price=limitPrice).result()
                                else:
                                    client.Order.Order_new(symbol='XBTUSD', orderQty=quantity, ordType="Limit",
                                                           side='Sell', price=limitPrice).result()

                            if (asset == "ETH"):
                                client.Position.Position_updateLeverage(symbol='ETHUSD', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='ETHUSD', orderQty=quantity, ordType="Limit",
                                                           side='Buy', price=limitPrice).result()
                                else:
                                    client.Order.Order_new(symbol='ETHUSD', orderQty=quantity, ordType="Limit",
                                                           side='Sell', price=limitPrice).result()

                            if (asset == "XRP"):
                                client.Position.Position_updateLeverage(symbol='XRPU19', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='XRPU19', orderQty=quantity, ordType="Limit",
                                                           side='Buy', price=limitPrice).result()
                                else:
                                    client.Order.Order_new(symbol='XRPU19', orderQty=quantity, ordType="Limit",
                                                           side='Sell', price=limitPrice).result()

                        if (ordertype == "CONDITIONAL"):
                            print("con")
                            if (asset == "BTC"):
                                client.Position.Position_updateLeverage(symbol='XBTUSD', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='XBTUSD', orderQty=quantity, ordType="MarketIfTouched",
                                                           side='Sell', stopPx=triggerPrice).result()
                                else:
                                    client.Order.Order_new(symbol='XBTUSD', orderQty=quantity, ordType="MarketIfTouched",
                                                           side='Buy', stopPx=triggerPrice).result()

                            if (asset == "ETH"):
                                client.Position.Position_updateLeverage(symbol='ETHUSD', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='ETHUSD', orderQty=quantity, ordType="MarketIfTouched",
                                                           side='Sell', stopPx=triggerPrice).result()
                                else:
                                    client.Order.Order_new(symbol='ETHUSD', orderQty=quantity, ordType="MarketIfTouched",
                                                           side='Buy', stopPx=triggerPrice).result()

                            if (asset == "XRP"):
                                client.Position.Position_updateLeverage(symbol='XRPU19', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='XRPU19', orderQty=quantity, ordType="MarketIfTouched",
                                                           side='Sell', stopPx=triggerPrice).result()
                                else:
                                    client.Order.Order_new(symbol='XRPU19', orderQty=quantity, ordType="MarketIfTouched",
                                                           side='Buy', stopPx=triggerPrice).result()

                        if(ordertype == "STOP"):
                            if (asset == "BTC"):
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='XBTUSD', execInst='Close', ordType="Stop",
                                                           side='Sell', stopPx=stoploss).result()
                                else:
                                    client.Order.Order_new(symbol='XBTUSD', execInst='Close', ordType="Stop",
                                                           side='Buy', stopPx=stoploss).result()

                            if (asset == "ETH"):
                                client.Position.Position_updateLeverage(symbol='ETHUSD', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='ETHUSD', execInst='Close', ordType="Stop",
                                                           side='Sell', stopPx=stoploss).result()
                                else:
                                    client.Order.Order_new(symbol='ETHUSD', execInst='Close', ordType="Stop",
                                                           side='Buy', stopPx=stoploss).result()

                            if (asset == "XRP"):
                                client.Position.Position_updateLeverage(symbol='XRPU19', leverage=leverage).result()
                                if (orderside == "LONG"):
                                    client.Order.Order_new(symbol='XRPU19', execInst='Close', ordType="Stop",
                                                           side='Sell', stopPx=stoploss).result()
                                else:
                                    client.Order.Order_new(symbol='XRPU19', execInst='Close', ordType="Stop",
                                                           side='Buy', stopPx=stoploss).result()

                        if (ordertype == "TAKEPROFIT"):
                            if (asset == "BTC"):
                                if (orderside == "SHORT"):
                                    client.Order.Order_new(symbol='XBTUSD', execInst='Close', ordType="MarketIfTouched",
                                                           side='Buy', stopPx=stoploss).result()
                                else:
                                    client.Order.Order_new(symbol='XBTUSD', execInst='Close', ordType="MarketIfTouched",
                                                           side='Sell', stopPx=stoploss).result()

                            if (asset == "ETH"):
                                client.Position.Position_updateLeverage(symbol='ETHUSD', leverage=leverage).result()
                                if (orderside == "SHORT"):
                                    client.Order.Order_new(symbol='ETHUSD', execInst='Close', ordType="MarketIfTouched",
                                                           side='Buy', stopPx=stoploss).result()

                                else:
                                    client.Order.Order_new(symbol='ETHUSD', execInst='Close', ordType="MarketIfTouched",
                                                           side='Sell', stopPx=stoploss).result()

                            if (asset == "XRP"):
                                client.Position.Position_updateLeverage(symbol='XRPU19', leverage=leverage).result()
                                if (orderside == "SHORT"):
                                    client.Order.Order_new(symbol='XRPU19', execInst='Close', ordType="MarketIfTouched",
                                                           side='Buy', stopPx=stoploss).result()

                                else:
                                    client.Order.Order_new(symbol='XRPU19', execInst='Close', ordType="MarketIfTouched",
                                                           side='Sell', stopPx=stoploss).result()


                    except:
                        print(traceback.format_exc())
                    
                file = open("src/de/skrrt/stacy/BitmexAPI/order.txt", "w")
                file.write("none")
                file.close()
                try:
                    file = open("src/de/skrrt/stacy/BitmexAPI/cancelOrder.txt", "r")
                    line = file.readline()
                    file.close()
                    if line.strip() != "none":
                        try:
                            print(line.strip())
                            client.Order.Order_cancel(orderID=line.strip()).result()
                        except:
                            print(traceback.format_exc())
                        file = open("src/de/skrrt/stacy/BitmexAPI/cancelOrder.txt", "w")
                        file.write("none")

                except:
                    print(traceback.format_exc())

                try:
                    file = open("src/de/skrrt/stacy/BitmexAPI/closePosition.txt", "r")
                    line = file.readline()
                    file.close()
                    if line.strip() != "none":
                        try:
                            print(line.strip())
                            client.Order.Order_new(symbol=line.strip(), execInst='Close', ordType="Market").result()
                        except:
                            print(traceback.format_exc())
                        file = open("src/de/skrrt/stacy/BitmexAPI/closePosition.txt", "w")
                        file.write("none")

                except:
                    print(traceback.format_exc())


        t1 = Thread(target=updatePrices)
        threading.Thread.__init__(self)
        t1.start()
        t2 = Thread(target=check4orders)
        threading.Thread.__init__(self)
        t2.start()
        t3 = Thread(target=updatePos())
        threading.Thread.__init__(self)
        t3.start()




    def testbuy(self, client):
        symbol = 'XBTUSD'
        qty = 1000
        price = 10000
        leverage = 50
        client.Position.Position_updateLeverage(symbol=symbol, leverage=leverage).result()
        client.Order.Order_new(symbol=symbol, orderQty=qty, ordType="Market").result()
        client.Order.closePosition(symbol=symbol)

    def testpos(self, client):
        pos = client.Position.Position_get().result()

        x = len(pos[0])
        i = 0
        while i < x:
            print(pos[0][i]['symbol'])
            print(pos[0][i]['leverage'])
            print(pos[0][i]['currentQty'])
            print(pos[0][i]['markPrice'])
            print(pos[0][i]['liquidationPrice'])
            i = i + 1

    def testopenorder(self, client):
        pos = client.Order.Order_getOrders(filter="{\"open\": true}").result()
        x = len(pos[0])
        i = 0
        while i < x:
            print(pos[0][i]['symbol'])
            print(pos[0][i]['ordType'])
            print(pos[0][i]['orderID'])
            print(pos[0][i]['stopPx'])
            i = i + 1

    def testcancelOrder(self, client):
        client.Order.Order_cancel(orderID="8a0c036a-52be-ce7c-11e9-4b4c95af2ac1").result()