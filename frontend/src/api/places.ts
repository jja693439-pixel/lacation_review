import type { PlaceSearchResult, SavedPlace } from '../types/place';

export async function searchPlaces(query: string): Promise<PlaceSearchResult[]> {
  const res = await fetch(`/api/places/search?q=${encodeURIComponent(query)}`);
  if (!res.ok) {
    throw new Error(`장소 검색 실패: ${res.status}`);
  }
  return res.json();
}

export async function savePlace(place: PlaceSearchResult): Promise<SavedPlace> {
  const res = await fetch('/api/places', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(place),
  });
  if (!res.ok) {
    throw new Error(`장소 저장 실패: ${res.status}`);
  }
  return res.json();
}

export async function listPlaces(): Promise<SavedPlace[]> {
  const res = await fetch('/api/places');
  if (!res.ok) {
    throw new Error(`장소 목록 조회 실패: ${res.status}`);
  }
  return res.json();
}
