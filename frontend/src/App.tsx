import { useEffect, useState } from 'react';
import { listPlaces } from './api/places';
import type { SavedPlace } from './types/place';
import { PlaceSearch } from './components/PlaceSearch';
import { PlaceList } from './components/PlaceList';
import { ReviewPanel } from './components/ReviewPanel';
import './App.css';

function App() {
  const [places, setPlaces] = useState<SavedPlace[]>([]);
  const [selectedId, setSelectedId] = useState<number | null>(null);

  useEffect(() => {
    listPlaces().then(setPlaces);
  }, []);

  function handleSaved(place: SavedPlace) {
    setPlaces((prev) => (prev.some((p) => p.id === place.id) ? prev : [...prev, place]));
    setSelectedId(place.id);
  }

  const selectedPlace = places.find((p) => p.id === selectedId) ?? null;

  return (
    <div className="app">
      <h1>장소 후기 기록</h1>
      <div className="layout">
        <div className="left">
          <PlaceSearch onSaved={handleSaved} />
          <PlaceList places={places} selectedId={selectedId} onSelect={setSelectedId} />
        </div>
        <div className="right">
          {selectedPlace ? (
            <ReviewPanel place={selectedPlace} />
          ) : (
            <p>왼쪽에서 장소를 검색해서 저장하거나, 저장된 장소를 선택하세요.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default App;
