import json
from typing import Optional, Any


def handler(event: Any, context: Any):
    print("request: " + json.dumps(event))

    body: str = ""
    query: Optional[str] = event.queryStringParameters.name

    if query:
        body = f"Hello {query.title()}!"
    else:
        body = "Hello world!"

    return {"statusCode": 200, "headers": {"Content-Type": "text/plain"}, "body": body}
