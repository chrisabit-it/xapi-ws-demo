import json
import re
from pandas import Timestamp, Timedelta

symbol_regexp = '^US[\\d]+$'
with open('push_rates.txt', 'r') as f:
	push_rates = []
	symbols = set()

	for line in f:
		push_rate = json.loads(line)
		symbol = push_rate['symbol']
		if re.match(symbol_regexp, symbol) is None:
			continue
		symbols.add(symbol)
		push_rates.append(dict(symbol=symbol, ts=Timestamp(push_rate['ts']['$date']), ask=push_rate['ask'], bid=push_rate['bid']))

	for symbol in symbols:
		symbol_rates = [e for e in push_rates if e['symbol'] == symbol]
		symbol_rates.sort(key=lambda x: x['ts'])
		l = len(symbol_rates)
		conv_rates = []
		for i in range(l):
			act_p = symbol_rates[i]
			if i < l - 1:
				b = act_p['ts']
				e = symbol_rates[i + 1 if i < l - 1 else 0]['ts']
				d: Timedelta = e - b
				ms = int(d.total_seconds() * 1000)
			else:
				ms = 1000
			assert ms >= 0
			if ms > 0:
				conv_rates.append(dict(ms=ms, ask=act_p['ask'], bid=act_p['bid']))

		json.dump(conv_rates, open(f'{symbol}.json', 'w'))
