# YRITYKSET

Q&D (quick and dirty) script for finding company addresses.

This script will use the PRH public API (http://avoindata.prh.fi/bis/v1/) to find addressess for companies listed in the input file.

Because of usage restrictions in the API the program is throttled to execute only one query per second.

## Usage

Create an input csv file named 'yritykset-in.csv' in the following format:

name,business-id

(business-id is the Finnish y-tunnus)

Make sure the company names do not contain commas

The input file should be located at project root

Run the program with `lein run`

The resulting addresses will be printed in the file 'yritykset-out.csv'

## License

Copyright © 2019 Gofore Oyj

MIT License
