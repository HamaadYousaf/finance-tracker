import { useEffect, useState } from "react";
import { getExpenses } from "../services/api";

function ExpenseList() {
    const [expenses, setExpenses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const userId = localStorage.getItem("userId");

    useEffect(() => {
        const fetchExpenses = async () => {
            try {
                if (!userId || isNaN(Number(userId))) {
                    throw new Error("Invalid userId");
                }

                const data = await getExpenses(Number(userId));
                setExpenses(data);
            } catch (err) {
                setError("Failed to load expenses");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchExpenses();
    }, [userId]);

    if (loading) {
        return <p className="text-gray-500">Loading expenses...</p>;
    }

    if (error) {
        return <p className="text-red-500">{error}</p>;
    }

    if (!expenses.length) {
        return <p className="text-gray-500">No expenses found</p>;
    }

    return (
        <div className="space-y-3">
            {expenses.map((expense) => (
                <div
                    key={expense.id}
                    className="flex justify-between items-center border-b pb-2"
                >
                    <div>
                        <p className="font-medium text-gray-800">
                            {expense.description}
                        </p>
                        <p className="text-sm text-gray-500">
                            {expense.category} • {expense.date}
                        </p>
                    </div>

                    <p className="font-semibold text-red-500">
                        -${Number(expense.amount).toFixed(2)}
                    </p>
                </div>
            ))}
        </div>
    );
}

export default ExpenseList;