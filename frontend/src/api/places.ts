import type { Place } from '../types/place';

export async function searchPlaces(query: string): Promise<Place[]> {
  const res = await fetch(`/api/places/search?q=${encodeURIComponent(query)}`);
  if (!res.ok) {
    throw new Error(`장소 검색 실패: ${res.status}`);
  }
  return res.json();
}
