# rest-keysetpagination-example
![Build](https://github.com/gregwhitaker/rest-keysetpagination-example/workflows/Build/badge.svg)

An example of [keyset pagination](https://use-the-index-luke.com/no-offset) in a RESTful webservice.

## Prerequisites
This example requires a running PostgreSQL database.

You can start an instance as a Docker container by running the following command:

    docker run -p 5432:5432 postgres

## Building the Example
Run the following command to build the example:

    ./gradlew clean build

## Running the Example
Follow the steps below to run the example:

1. Run the following command to start the example service:

        ./gradlew bootRun
        
2. Run the following command to query a list of all of the employees in the company:

        curl http://localhost:8080/employees
        
    If successful, you will receive 25 employee records from the database with a link containing a cursor to get the next 25 records.
    
        {
          "count": 25,
          "employees": [
            ...
          ],
          "links": [
            {
              "rel": "next",
              "type": "GET",
              "href": "/employees?cursor=VapqQ"
            }
          ]
        }
    
3. Adjust the url to add the next cursor to paginate through the results:

        curl http://localhost:8080/employees?cursor=VapqQ
        
    If successful, you will retrieve employee records `26` - `50`:

## Bugs and Feedback
For bugs, questions, and discussions please use the [Github Issues](https://github.com/gregwhitaker/rest-keysetpagination-example/issues).

## License
MIT License

Copyright (c) 2021 Greg Whitaker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
