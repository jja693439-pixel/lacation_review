import type { SavedPlace } from '../types/place';

interface Props {
  places: SavedPlace[];
  selectedId: number | null;
  onSelect: (id: number) => void;
}

export function PlaceList({ places, selectedId, onSelect }: Props) {
  return (
    <section>
      <h2>저장한 장소</h2>
      {places.length === 0 && <p>아직 저장한 장소가 없습니다.</p>}
      <ul className="place-list">
        {places.map((place) => (
          <li key={place.id}>
            <button
              type="button"
              className={place.id === selectedId ? 'selected' : ''}
              onClick={() => onSelect(place.id)}
            >
              {place.name}
            </button>
          </li>
        ))}
      </ul>
    </section>
  );
}
