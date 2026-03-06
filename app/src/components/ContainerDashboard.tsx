import { useEffect, useState } from "react"
import { getContainers } from "../api/containerApi"
import { TrashContainer } from "../types/trash"

export default function ContainerDashboard() {

  const [containers, setContainers] = useState<TrashContainer[]>([])

  const loadContainers = async () => {
    try {
      const data = await getContainers()
      setContainers(data)
    } catch (error) {
      console.error(error)
    }
  }

  useEffect(() => {

    loadContainers()

    const interval = setInterval(loadContainers, 5000)

    return () => clearInterval(interval)

  }, [])

  return (
    <div>
      <h1>Smart Waste Dashboard</h1>

      {containers.map((c, index) => (

        <div key={index} style={{border:"1px solid gray", margin:"10px", padding:"10px"}}>

          <h3>Bin {c.id}</h3>
          <p>name {c.name}</p>
          <p>location{c.location}</p>
          <p>Fill level: {c.fillPercentage}%</p>
          <p>lastUpdated{c.lastUpdated}</p>
          <p>TrashType{c.type}</p>


          <p>Last update: {c.lastUpdated}</p>

        </div>

      ))}
    </div>
  )
}