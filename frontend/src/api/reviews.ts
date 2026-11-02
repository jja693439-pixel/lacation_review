import type { Review, ReviewInput } from '../types/review';

export async function listReviews(placeId: number): Promise<Review[]> {
  const res = await fetch(`/api/places/${placeId}/reviews`);
  if (!res.ok) {
    throw new Error(`후기 목록 조회 실패: ${res.status}`);
  }
  return res.json();
}

export async function createReview(placeId: number, input: ReviewInput): Promise<Review> {
  const res = await fetch(`/api/places/${placeId}/reviews`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(input),
  });
  if (!res.ok) {
    throw new Error(`후기 작성 실패: ${res.status}`);
  }
  return res.json();
}

export async function updateReview(id: number, input: ReviewInput): Promise<Review> {
  const res = await fetch(`/api/reviews/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(input),
  });
  if (!res.ok) {
    throw new Error(`후기 수정 실패: ${res.status}`);
  }
  return res.json();
}

export async function deleteReview(id: number): Promise<void> {
  const res = await fetch(`/api/reviews/${id}`, { method: 'DELETE' });
  if (!res.ok) {
    throw new Error(`후기 삭제 실패: ${res.status}`);
  }
}
