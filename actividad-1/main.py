
from fastapi import FastAPI, HTTPException

app = FastAPI()


@app.get("/resta/{a}/{b}")
def resta(a: int, b: int):
    if a == b:
        raise HTTPException(
            status_code=400, detail="'a' no puede ser igual a 'b'")
    if a < b:
        raise HTTPException(
            status_code=400, detail="'a' no puede ser menor que 'b'")

    return {"resultado": a - b}
