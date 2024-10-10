import asyncio
import json
import logging
import xapi

logging.basicConfig(level=logging.INFO)

with open("credentials.json", "r") as f:
    CREDENTIALS = json.load(f)

async def main() -> dict:
    try:
        async with await xapi.connect(**CREDENTIALS) as x:
            print('ping: ', await x.socket.ping())
            print('version: ', await x.socket.getVersion())

    except xapi.LoginFailed as e:
        print(f"Log in failed: {e}")

    except xapi.ConnectionClosed as e:
        print(f"Connection closed: {e}")

if __name__ == "__main__":
    asyncio.run(main())
