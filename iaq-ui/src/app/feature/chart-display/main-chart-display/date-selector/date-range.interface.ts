import { RealTimeDateUnit } from "./realtime-date-selector/real-time-unit.enum";

export interface DateRange {
    start: Date;
    end: Date;
    realtime: boolean;
    realtimeData?: {
        unit: RealTimeDateUnit
        time: number
    }
}