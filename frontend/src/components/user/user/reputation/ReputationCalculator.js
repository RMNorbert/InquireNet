const ranks = ["Chaos Creator", "Rebel Without a Cause", "Mischief Maker", "Troublemaker",
    "Beginner", "Novice", "Apprentice", "Enthusiast", "Contributor", "Expert", "Legend"]
const upVotesIndex = 0
const downVotesIndex = 1
const reputationModifier = 10.0;

export const getRank = (votes) => {
    const result = Math.round(reputationModifier - (reputationModifier * (votes[downVotesIndex] /
        (votes[upVotesIndex] + votes[downVotesIndex]))));
    return ranks[result];
}

