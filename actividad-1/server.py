from fastapi import FastAPI, HTTPException

app = FastAPI()


@app.get("/resta/{a}/{b}")
def resta(a: int, b: int):
    try:
        if a == b:
            raise HTTPException(
                status_code=400, detail="'a' no puede ser igual a 'b'")
        elif a < b:
            raise HTTPException(
                status_code=400, detail="'a' no puede ser menor que 'b'")
        else:
            return {"resultado": a - b}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
