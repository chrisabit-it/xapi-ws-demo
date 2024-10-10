import asyncio
import json
import logging
from pprint import pprint

import xapi

logging.basicConfig(level=logging.INFO)

with open("credentials.json", "r") as f:
    CREDENTIALS = json.load(f)

async def main() -> dict:
    try:
        async with await xapi.connect(**CREDENTIALS) as x:
            await x.stream.getTickPrices("US30")
            await x.stream.getTickPrices("US100")
            await x.stream.getTickPrices("US500")
            await x.stream.getTickPrices("US2000")

            async for message in x.stream.listen():
                print(message['data'])

    except xapi.LoginFailed as e:
        print(f"Log in failed: {e}")

    except xapi.ConnectionClosed as e:
        print(f"Connection closed: {e}")

if __name__ == "__main__":
    asyncio.run(main())
