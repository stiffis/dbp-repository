from pydantic import BaseModel


class OperacionRequest(BaseModel):
    a: float
    b: float
