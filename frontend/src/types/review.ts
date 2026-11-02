export interface Review {
  id: number;
  placeId: number;
  review: string;
  visitDate: string; // YYYY-MM-DD
  rating: number;
}

export interface ReviewInput {
  review: string;
  visitDate: string;
  rating: number;
}
