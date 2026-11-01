export interface Place {
  kakaoPlaceId: string;
  name: string;
  address: string;
  x: number; // 경도
  y: number; // 위도
  category: string;
}
