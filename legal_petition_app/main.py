from fastapi import FastAPI

from app.api.controllers.legal_petition_controller import router as legal_petition_router

app = FastAPI(title= "Legal Petition App")

app.include_router(legal_petition_router, prefix="/legal-petition")