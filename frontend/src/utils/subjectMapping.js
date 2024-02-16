export function subjectMapping(subject) {
  const subjectMap = {
    Korean: '국어',
    English: '영어',
    Math: '수학',
    Society: '사회',
    Science: '과학',
    Athletic: '체육',
    Japanese: '일본어',
    Chinese: '중국어',
    IT: 'IT'
  };
  return subjectMap[subject] || '자유공부방';
}
