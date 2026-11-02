import { useState, type FormEvent } from 'react';
import { savePlace, searchPlaces } from '../api/places';
import type { PlaceSearchResult, SavedPlace } from '../types/place';

interface Props {
  onSaved: (place: SavedPlace) => void;
}

export function PlaceSearch({ onSaved }: Props) {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState<PlaceSearchResult[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleSearch(e: FormEvent) {
    e.preventDefault();
    if (!query.trim()) return;
    setLoading(true);
    setError(null);
    try {
      setResults(await searchPlaces(query));
    } catch {
      setError('검색에 실패했습니다.');
    } finally {
      setLoading(false);
    }
  }

  async function handleSave(place: PlaceSearchResult) {
    try {
      onSaved(await savePlace(place));
    } catch {
      setError('저장에 실패했습니다.');
    }
  }

  return (
    <section>
      <h2>장소 검색</h2>
      <form onSubmit={handleSearch}>
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="장소 이름으로 검색 (예: 국립현대미술관)"
        />
        <button type="submit" disabled={loading}>
          {loading ? '검색 중...' : '검색'}
        </button>
      </form>
      {error && <p className="error">{error}</p>}
      <ul className="place-result-list">
        {results.map((place) => (
          <li key={place.kakaoPlaceId}>
            <div>
              <strong>{place.name}</strong>
              <p>{place.address}</p>
              <p className="category">{place.category}</p>
            </div>
            <button type="button" onClick={() => handleSave(place)}>
              저장
            </button>
          </li>
        ))}
      </ul>
    </section>
  );
}
