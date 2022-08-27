export interface Metric {
    id: string;
    name: string;
    description: string;
    unit: string;
    metadata: {
        color: string,
        backgroundColor: string,
        icon: string,
        min: number
    }
}