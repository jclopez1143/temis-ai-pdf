from abc import ABC, abstractmethod
from app.domain.entities.user_petition import UserPetition
from app.domain.entities.legal_petition import LegalPetition

class LegalPetitionService(ABC):

    @abstractmethod
    def generate_petition(self, user_petition: UserPetition) -> LegalPetition: pass