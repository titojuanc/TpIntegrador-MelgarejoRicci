export enum UserLevel {
  BRONZE = 'BRONCE',
  SILVER = 'PLATA',
  GOLD = 'ORO'
}

export interface Review {
  id: string;
  userName: string;
  rating: number;
  comment: string;
  date: string;
}

export interface Publication {
  id: string;
  title: string;
  price: number;
  category: string;
  description: string;
  imageUrl: string;
  promoted?: boolean;
}

export interface UserProfile {
  id: string;
  name: string;
  level: UserLevel;
  reputationPoints: number;
  publicationCount: number;
  reviews: Review[];
  publications: Publication[];
}