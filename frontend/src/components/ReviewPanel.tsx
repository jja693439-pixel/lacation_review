import { useEffect, useState, type FormEvent } from 'react';
import { createReview, deleteReview, listReviews, updateReview } from '../api/reviews';
import type { Review, ReviewInput } from '../types/review';
import type { SavedPlace } from '../types/place';

interface Props {
  place: SavedPlace;
}

const emptyForm: ReviewInput = { review: '', visitDate: '', rating: 5 };

export function ReviewPanel({ place }: Props) {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [form, setForm] = useState<ReviewInput>(emptyForm);
  const [editingId, setEditingId] = useState<number | null>(null);

  useEffect(() => {
    listReviews(place.id).then(setReviews);
    setForm(emptyForm);
    setEditingId(null);
  }, [place.id]);

  async function handleSubmit(e: FormEvent) {
    e.preventDefault();
    if (editingId === null) {
      const created = await createReview(place.id, form);
      setReviews((prev) => [...prev, created]);
    } else {
      const updated = await updateReview(editingId, form);
      setReviews((prev) => prev.map((r) => (r.id === editingId ? updated : r)));
    }
    setForm(emptyForm);
    setEditingId(null);
  }

  function handleEdit(review: Review) {
    setEditingId(review.id);
    setForm({ review: review.review, visitDate: review.visitDate, rating: review.rating });
  }

  async function handleDelete(id: number) {
    await deleteReview(id);
    setReviews((prev) => prev.filter((r) => r.id !== id));
    if (editingId === id) {
      setEditingId(null);
      setForm(emptyForm);
    }
  }

  return (
    <section>
      <h2>{place.name} 후기</h2>
      <ul className="review-list">
        {reviews.map((r) => (
          <li key={r.id}>
            <div>
              <p>{r.review}</p>
              <p className="meta">
                방문일 {r.visitDate} · 별점 {r.rating}
              </p>
            </div>
            <div className="review-actions">
              <button type="button" onClick={() => handleEdit(r)}>
                수정
              </button>
              <button type="button" onClick={() => handleDelete(r.id)}>
                삭제
              </button>
            </div>
          </li>
        ))}
      </ul>

      <form onSubmit={handleSubmit} className="review-form">
        <textarea
          value={form.review}
          onChange={(e) => setForm({ ...form, review: e.target.value })}
          placeholder="후기를 입력하세요"
          required
        />
        <input
          type="date"
          value={form.visitDate}
          onChange={(e) => setForm({ ...form, visitDate: e.target.value })}
          required
        />
        <select
          value={form.rating}
          onChange={(e) => setForm({ ...form, rating: Number(e.target.value) })}
        >
          {[5, 4, 3, 2, 1].map((n) => (
            <option key={n} value={n}>
              {n}점
            </option>
          ))}
        </select>
        <button type="submit">{editingId === null ? '후기 작성' : '수정 완료'}</button>
      </form>
    </section>
  );
}
