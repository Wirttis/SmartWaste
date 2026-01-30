//import { useState } from 'react'
import './App.css'
import { TrashContainerBox } from './components/TrashContainerBox';

interface TrashContainer {
  id: string;
  location: string;
  name: string;
  fill_percentage: number;
  last_updated: string;
}
// TODO: Fetch containers from API
const containers: TrashContainer[] = [
    {
    id: "1",
    name: "Vessan roskis",
    location: "Kerubin wc",
    fill_percentage: 85,
    last_updated: "2 hours ago"
  }, {
    id: "2",
    name: "Metalliroskis",
    location: "Rautatie 81",
    fill_percentage: 75,
    last_updated: "1 hours ago"}
];

function App() {
  // TODO: Filters and Sorting
  //const [filterType, setFilterType] = useState<string>("all");
  //const [sortBy, setSortBy] = useState<string>("fill-desc");

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
