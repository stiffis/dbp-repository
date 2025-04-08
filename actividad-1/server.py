from fastapi import FastAPI

app = FastAPI()


@app.get("/resta/{a}/{b}")
def resta(a: int, b: int):
    if a == b:
        return {"resultado": "a es igual a b"}
    elif a < b:
        return {"resultado": "a es menor que b"}
    else:
        return {"resultado": a - b}
