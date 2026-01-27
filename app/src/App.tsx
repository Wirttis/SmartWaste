import { useState } from 'react'
import './App.css'
import { TrashContainerBox } from './components/TrashContainerBox';

interface TrashContainer {
  id: string;
  location: string;
  name: string;
  fill_percentage: number;
  last_updated: string;
}
// Fetch containers from api
const containers: TrashContainer[] = [
    {
    id: "1",
    name: "Vessan roskis",
    location: "Kerubin wc",
    fill_percentage: 85,
    last_updated: "2 hours ago"
  }
];

function App() {
  // TODO: Filters and Sorting
  return (
    <>
      <div>
        {containers.map((container) => (
          <TrashContainerBox container={container} />
        ))}
      </div>
    </>
  )
}

export default App
