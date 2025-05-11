from typing import Annotated

from fastapi import APIRouter, Depends, status
from datetime import datetime

from app.domain.entities.user_petition import UserPetition
from app.domain.entities.legal_petition import LegalPetition
from app.application.interfaces.legal_petition_service import LegalPetitionService
from app.application.use_cases.legal_petition_service_impl import LegalPetitionServiceImpl


router = APIRouter()

def get_legal_petition_service() -> LegalPetitionService:
    return LegalPetitionServiceImpl()

LegalPetitionServiceDep = Annotated[LegalPetitionService, Depends(get_legal_petition_service)]

@router.post("/generate", status_code=status.HTTP_200_OK)
def generate(user_petition: UserPetition, legal_petition_service: LegalPetitionServiceDep):
    return legal_petition_service.generate_petition(user_petition)